﻿using System;
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
using System.Runtime.Serialization.Json;

namespace ZombieSharpClient
{
    class Program
    {
        public static void StartClient()
        {
            
           // JsonDataContractSerializer s;
        
               // byte[] msg = Encoding.ASCII.GetBytes("{\"name\":\"User1\",\"token\":null,\"action\":0,\"connectAction\":{\"pass\":\"12345\"}}");
            TcpClient client = new TcpClient("78.47.52.69", 18080);

          //  String message = "{\"name\":\"User1\",\"token\":null,\"action\":0,\"connectAction\":{\"pass\":\"12345\"}}]\n";
            UserAction action = new UserAction();
            action.Name = "User1";
            action.Action = 0;
            ConnectAction connect = new ConnectAction();
            connect.Pass = "12345";
            action.ConnectAction = connect;

            byte[] finalData;
            using (var stream = new MemoryStream())
            {
                var serializer = new DataContractJsonSerializer(typeof(UserAction));
                serializer.WriteObject(stream, action);
                finalData = stream.ToArray();
            }

            var text = Encoding.UTF8.GetString(finalData);
            Console.WriteLine("Sent: {0}", text);   
            Console.ReadLine();

            Byte[] data = finalData;

            Console.WriteLine(String.Join(",",data));   
            client.GetStream();
            NetworkStream stream1 = client.GetStream();

            stream1.Write(data, 0, data.Length);
            stream1.Write(Encoding.UTF8.GetBytes("\n"), 0, 1);
            stream1.Flush();

            data = new Byte[256];

            String responseData = String.Empty;

            Int32 bytes = stream1.Read(data, 0, data.Length);
            responseData = System.Text.Encoding.UTF8.GetString(data, 0, bytes);
            Console.WriteLine("Received: {0}", responseData); 
            int index1=responseData.IndexOf("token");
            int index2=responseData.IndexOf(",",index1);

            String token = responseData.Substring(index1 + 8, index2 - index1 - 9);
            Console.WriteLine("token: {0}", token); 
            
            String requestCard="{\"name\":\"User1\",\"token\":\""+token+"\",\"action\":70}";
            data = System.Text.Encoding.UTF8.GetBytes(requestCard);
            stream1.Write(data, 0, data.Length);
            stream1.Write(Encoding.UTF8.GetBytes("\n"), 0, 1);
            stream1.Flush();
            data = new Byte[10000];
            bytes = stream1.Read(data, 0, data.Length);
            responseData = System.Text.Encoding.UTF8.GetString(data, 0, bytes);
            Console.WriteLine("Received: {0}", responseData); 

            // Close everything.
            stream1.Close();         
            client.Close();
            Thread.Sleep(1000000);

    }

        static void Main(string[] args)
        {
              StartClient();
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
