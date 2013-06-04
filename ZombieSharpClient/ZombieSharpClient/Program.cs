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
using ZombieSharpClient.UserActions;
using ZombieSharpClient.UserReplys;
using System.Runtime.Serialization.Json;

namespace ZombieSharpClient
{
    class Program
    {

        

        public static void StartClient()
        {
            
           // JsonDataContractSerializer s;
        
               // byte[] msg = Encoding.ASCII.GetBytes("{\"name\":\"User1\",\"token\":null,\"action\":0,\"connectAction\":{\"pass\":\"12345\"}}");

            //----------
//            Client c = new Client();
//            c.sendConnect();
//            Console.ReadLine();
//
//            Console.WriteLine("Received: {0}", c.getList().Count);
//            c.sendConnect();
//            Console.ReadLine();
//            Console.WriteLine("Received: {0}", c.getList().Count);
//            c.sendConnect();
//            Console.ReadLine();
//            Console.WriteLine("Received: {0}", c.getList().Count);
//
//
//
//            TcpClient client = new TcpClient("78.47.52.69", 18080);
//
//          //  String message = "{\"name\":\"User1\",\"token\":null,\"action\":0,\"connectAction\":{\"pass\":\"12345\"}}]\n";
//            UserAction action = new UserAction();
//            action.Name = "User1";
//            action.Action = 0;
//            ConnectAction connect = new ConnectAction();
//            connect.Pass = "12345";
//            action.ConnectAction = connect;
//
//            byte[] finalData;
//            var serializer = new DataContractJsonSerializer(typeof(UserAction));
//            var deserializer = new DataContractJsonSerializer(typeof(UserReply));
//            using (var stream = new MemoryStream())
//            {
//               
//                serializer.WriteObject(stream, action);
//                finalData = stream.ToArray();
//            }
//
//            var text = Encoding.UTF8.GetString(finalData);
//            Console.WriteLine("Sent: {0}", text);   
//            Console.ReadLine();
//
//            Byte[] data = finalData;
//
//           // Console.WriteLine(String.Join(",",data));   
//            client.GetStream();
//            NetworkStream stream1 = client.GetStream();
//
//            stream1.Write(data, 0, data.Length);
//            stream1.Write(Encoding.UTF8.GetBytes("\n"), 0, 1);
//            stream1.Flush();
//
//            data = new Byte[10000];
//
//            String responseData = String.Empty;
//
//            Int32 bytes = stream1.Read(data, 0, data.Length);
//            var res = new Byte[bytes];
//            System.Array.Copy(data,res,bytes);
//            responseData = System.Text.Encoding.UTF8.GetString(data, 0, bytes);
//            Console.WriteLine("Received: {0}", responseData);



            //----------


//            Byte[] dcopy = new Byte[bytes];
//            for (int i = 0; i < bytes; i++)
//            {
//                dcopy[i] = data[i];
//            }
//            MemoryStream ms1 = new MemoryStream(dcopy);
//            MemoryStream ms2 = new MemoryStream(data);
//
//            UserReply usr=new UserReply();
//            usr.Reply = 70;
//            ConnectionReply cnr=new ConnectionReply();
//            cnr.Token = "234";
//            usr.ConnectionReply = cnr;
//            var tmp = new MemoryStream();
//            deserializer.WriteObject(tmp,usr);
//            var text1 = Encoding.UTF8.GetString(tmp.ToArray());
//            Console.WriteLine("Sent111: {0}", text1);
//            Console.ReadLine();
//
//            UserReply urs12= (UserReply)deserializer.ReadObject(new MemoryStream(tmp.ToArray()));
//            Console.WriteLine("Sent222: {0}", urs12);
//            Console.ReadLine();
//
//          //  var text2 = "{\"connectionReply\":{\"token\":\"234\",\"version\":null},\"reply\":70}";
//            var text2 = "{\"reply\":70,\"connectionReply\":{\"token\":\"234\",\"version\":null}}\n";
//            MemoryStream mstmp = new MemoryStream(Encoding.UTF8.GetBytes(text2));
//            UserReply urs13 = (UserReply)deserializer.ReadObject(mstmp);
//            Console.WriteLine("Sent333: {0}", urs13);
//            Console.ReadLine();

//            Console.WriteLine("dta: {0}", responseData);
//            UserReply readObject = (UserReply)deserializer.ReadObject(new MemoryStream(res));
//            String token = readObject.ConnectionReply.Token;
//          
//            Console.WriteLine("token: {0}", token); 
//            
//            String requestCard="{\"name\":\"User1\",\"token\":\""+token+"\",\"action\":70}";
//            data = System.Text.Encoding.UTF8.GetBytes(requestCard);
//            stream1.Write(data, 0, data.Length);
//            stream1.Write(Encoding.UTF8.GetBytes("\n"), 0, 1);
//            stream1.Flush();
//            data = new Byte[10000];
//            bytes = stream1.Read(data, 0, data.Length);
//            responseData = System.Text.Encoding.UTF8.GetString(data, 0, bytes);
//            Console.WriteLine("Received: {0}", responseData); 
//
//            // Close everything.
//            stream1.Close();         
//            client.Close();
//            Thread.Sleep(1000000);

    }

         public static void bots()
         {
             GameBot c1 = new GameBot("User1","12345");
             GameBot c2 = new GameBot("User2", "123456");
             c1.Start();
             c2.Start();
             while (c1.isAlive && c2.isAlive)
             {
                 Thread.Sleep(100);
             }

         }



        static void Main(string[] args)
        {
            //  StartClient();

            bots();

            /*  Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp); //Создаем основной сокет
               IPAddress ipAddress = null; //IP-адресс
               IPEndPoint Addr = null; //конечная точка(IP и порт)
               ipAddress = Dns.GetHostEntry("127.0.0.1").AddressList[0];
               Addr = new IPEndPoint(ipAddress, 18080); //"localhost" = 127.0.0.1
               s.Connect(Addr); //Коннектимся к срверу
               while (true) //Вечная истина :)
               {
                   byte[] msg = Encoding.UTF8.GetBytes("{\"name\":\"User1\",\"token\":null,\"action\":0,\"connectAction\":{\"pass\":\"12345\"}}"); //Конвертируем
                   s.Send(msg); //Отправляем
               }*/
        }
    }
}
