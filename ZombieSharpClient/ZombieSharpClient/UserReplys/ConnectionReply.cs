using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using ZombieSharpClient.Entity.Game;
using ZombieSharpClient.Entity.Server;
using ZombieSharpClient.Entity.Support;
namespace ZombieSharpClient.UserReplys
{
	[DataContract]
	public class ConnectionReply{
		[DataMember(Name = "token")]
		public string Token { get; set; }
		[DataMember(Name = "version")]
		public string Version { get; set; }
	}
}