using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Collections;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Timers;
using ZombieSharpClient.Actions;
using ZombieSharpClient.UserActions;
using ZombieSharpClient.UserReplys;
using System.Runtime.Serialization.Json;
using System.Collections.Concurrent;
using System.Threading;

namespace ZombieSharpClient
{
    class Client
    {
        TcpClient client = new TcpClient("78.47.52.69", 18080);
        NetworkStream stream1;
        List<UserReply> reponses = new List<UserReply>();
        DataContractJsonSerializer serializer;
        DataContractJsonSerializer deserializer;
        public String token { set; get; }

        private String locname { set; get; }
        private String pass { set; get; }
        public event EventHandler<UserActionArgs> onReceived;
        private StreamWriter file;

        private Boolean isRunning { get; set; }
        public Client(String name, String pass,StreamWriter file)
        {
            this.file = file;
            locname = name;
            pass = pass;
            stream1= client.GetStream();
            isRunning = true;
            Thread t=new Thread(this.awaitResponse);
            t.Start();
            serializer = new DataContractJsonSerializer(typeof(UserAction));
            deserializer = new DataContractJsonSerializer(typeof(UserReply));
            sendConnect(name, pass);

        }
        public void stop(){
            isRunning=false;
            Thread.Sleep(1000);
            stream1.Close();
            client.Close();
        }
        public List<UserReply> getList()
        {
            return reponses;
        }

        public void createUser(String name, String pass, int side)
        {
            UserAction action = new UserAction();
            action.Name = name;
            action.Action = (int)ActionTypeEnum.CREATE_USER;
            CreateUserAction connect = new CreateUserAction();
            connect.Name = name;
            connect.Pass = pass;
            connect.Side = side;
            action.CreateUserAction = connect;
            send(action);

        }
        private void send(UserAction action){
            byte[] finalData;
            using (var stream = new MemoryStream())
            {

                serializer.WriteObject(stream, action);
                finalData = stream.ToArray();
            }

            var text = Encoding.UTF8.GetString(finalData);
            Console.WriteLine("Sent: {0}", text);
            file.WriteLine("send:" + text);

            Byte[] data = finalData;

            stream1.Write(data, 0, data.Length);
            stream1.Write(Encoding.UTF8.GetBytes("\n"), 0, 1);
            stream1.Flush();
        }

        public void cardRequest()
        {
            UserAction action = new UserAction();
            action.Name = locname;
            action.Token = token;
            action.Action = (int)ActionTypeEnum.GET_CARD_INFO; ;
            CardInfoAction act=new CardInfoAction();
            send(action);
        }


        public void sendConnect(String name, String pass){
            UserAction action = new UserAction();
            action.Name = name;
            action.Action = (int)ActionTypeEnum.CONNECT; ;
            ConnectAction connect = new ConnectAction();
            connect.Pass = pass;
            action.ConnectAction = connect;
            send(action);
        }

        public void sendSearch()
        {
            UserAction action = new UserAction();
            action.Name = locname;
            action.Token = token;
            action.Action = (int)ActionTypeEnum.SEARCH;
            send(action);
        }

        public void sendSurrender(int turnNumber)
        {
            UserAction action = new UserAction();
            action.Name = locname;
            action.Token = token;
            action.Action = (int)ActionTypeEnum.TURN;
            TurnAction ta = new TurnAction();
            ta.Action=(int)TurnActionEnum.surrender;
            ta.TurnNumber=turnNumber;
            action.TurnAction = ta;
            send(action);
        }

        public void sendEndTurn(int turnNumber)
        {
            UserAction action = new UserAction();
            action.Name = locname;
            action.Token = token;
            action.Action = (int)ActionTypeEnum.TURN;
            TurnAction ta = new TurnAction();
            ta.Action = (int)TurnActionEnum.endturn;
            ta.TurnNumber = turnNumber;
            action.TurnAction = ta;
            send(action);
        }

        public void sendTurnAction(int turnNumber, long cardId, int position)
        {

             UserAction action = new UserAction();
             action.Name = locname;
             action.Token = token;
             action.Action = (int)ActionTypeEnum.TURN;
             TurnAction ta = new TurnAction();
             ta.Action = (int)TurnActionEnum.turn;
             ta.CardId = cardId;
             ta.Position = position;
             ta.TurnNumber = turnNumber;
             action.TurnAction = ta;
             send(action);
    }



        public void sendTurn(TurnActionEnum act,int position, long cardId,int turnNum)
        {
            UserAction action = new UserAction();
            action.Name = locname;
            action.Token = token;
            action.Action = (int)ActionTypeEnum.TURN;
            TurnAction turn=new TurnAction();
            turn.Action = (int)act;
            turn.Position = position;
            turn.CardId = cardId;
            turn.TurnNumber = turnNum;
            action.TurnAction = turn;
            send(action);

        }

        public void awaitResponse()
        {

            while(isRunning){
               
                Byte[] data = new Byte[10000];
                String responseData = String.Empty;
                Int32 bytes = stream1.Read(data, 0, data.Length);
                var res = new Byte[bytes];
                System.Array.Copy(data, res, bytes);
                responseData = System.Text.Encoding.UTF8.GetString(data, 0, bytes);
                //thisLock.AcquireWriterLock(1000000);
                UserReply resD= (UserReply)deserializer.ReadObject(new MemoryStream(res));
                reponses.Add(resD);
                Console.WriteLine("Received: {0}", responseData);
                file.WriteLine(responseData);
                if (onReceived != null)
                {
                    onReceived.Invoke(this, new UserActionArgs(resD));
                }
            }
        }

        

    }
}
