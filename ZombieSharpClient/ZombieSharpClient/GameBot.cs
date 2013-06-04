using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using ZombieSharpClient;
using ZombieSharpClient.Entity.Game;
using ZombieSharpClient.Replys;
using ZombieSharpClient.UserReplys;

namespace ZombieSharpClient
{
    internal class GameBot
    {
        public Dictionary<long, Card> cards = new Dictionary<long, Card>();
        public List<Card> currcards = new List<Card>();
        private Client client;
        public Boolean isAlive = true;
        private String username;
        private String pass;
        public int playerQueue = 0;
        private System.IO.StreamWriter file;


        private int currTurn = 0;

        public GameBot(String username, String pass)
        {
            this.username = username;
            this.pass = pass;
           

        }
        public void Start()
        {
            Thread th=new Thread(startTh);
            th.Start();
        }

        public void startTh()
        {
            file =
                new System.IO.StreamWriter(
                    Path.GetDirectoryName(Assembly.GetEntryAssembly().Location) + "/" + username + ".txt", true);
            client = new Client(username, pass,file);

            int lastid = 0;
            while (true)
            {
                for (int i = lastid; i < client.getList().Count(); i++)
                {
                    if (client.getList().ElementAt(i).Reply == (int)ReplyTypeEnum.CONNECTION)
                    {
                        client.token = client.getList().ElementAt(i).ConnectionReply.Token;
                        lastid = i;
                        goto EndConnect;
                    }
                    lastid = i;
                }
                Thread.Sleep(100);
            }
        EndConnect:
            client.cardRequest();
            while (true)
            {
                for (int i = lastid; i < client.getList().Count(); i++)
                {
                    if (client.getList().ElementAt(i).Reply == (int)ReplyTypeEnum.GET_CARD_INFO)
                    {
                        foreach (Fraction f in client.getList().ElementAt(i).CardInfoReply.Fractions)
                        {
                            foreach (SubFraction sub in f.SubFractions)
                            {
                                foreach (Card c in sub.Deck)
                                {
                                    cards.Add(c.Id, c);
                                }

                            }
                        }
                        lastid = i;
                        goto EndCardInfo;
                    }
                    lastid = i;
                }
                Thread.Sleep(100);
            }
        EndCardInfo:
            client.sendSearch();
            while (true)
            {
                for (int i = lastid; i < client.getList().Count(); i++)
                {
                    if (client.getList().ElementAt(i).Reply == (int)ReplyTypeEnum.GAME_STARTED)
                    {
                        UserReply rep = client.getList().ElementAt(i);
                        foreach (long l in rep.GameStartedReply.Cards)
                        {
                            currcards.Add(cards[l]);
                        }
                        playerQueue = rep.GameStartedReply.Position;
                        lastid = i;
                        goto EndSearch;
                    }
                    lastid = i;
                }
                Thread.Sleep(100);
            }
        EndSearch:
            client.onReceived += onReceived;
            while (isAlive)
            {
                Thread.Sleep(100);
            }
            client.stop();
            file.Close();  
        }

        public void onReceived(object sender, UserActionArgs e)
        {
            if (e.reply.Reply != (int) ReplyTypeEnum.TURN)
                return;

            TurnReply rep = e.reply.TurnReply;

            if (rep.Action == (int) TurnReplyEnum.turn)
            {
                //nope
            }
            if (rep.Action == (int) TurnReplyEnum.uwin)
            {
                isAlive = false;
                //  System.out.println("END!!!!"+Long.toString(sumDate) +"/"+ Long.toString(sumDate/bot.currTurn) );
                Console.WriteLine("player: {0} win", username);
            }
            if (rep.Action == (int) TurnReplyEnum.ulose)
            {
                isAlive = false;
                //   System.out.println("END!!!!"+Long.toString(sumDate)+"/"+ Long.toString(sumDate/bot.currTurn) );
                Console.WriteLine("player: {0} loose", username);
            }
            if (currTurn >= 50)
            {
                client.sendSurrender(currTurn);
            }
            else if (rep.Action == (int) TurnReplyEnum.endturn)
            {
                if (rep.NextTurnUser == playerQueue)
                {
                    //  System.out.println("turn zombies.dto.reply "+bot.getUsername()+"/"+bot.currTurn+"/");
                    //  Console.WriteLine("player: {0} loose", username);
                    currcards.Clear();
                    currTurn = rep.TurnNumber + 1;
                    long activeCard = -1;
                    foreach (long l in rep.PlayerHand)
                    {
                        Card c = cards[l];
                        if (c.CardType == (int) CardTypeEnum.creature)
                        {
                            activeCard = l;
                        }
                        currcards.Add(cards[l]);

                    }
                    if (activeCard != -1)
                    {
                        client.sendTurnAction(currTurn, activeCard, 0);
                    }
                    Thread.Sleep(1000);
                    client.sendEndTurn(currTurn);
                }
            }
        }
    }
}
