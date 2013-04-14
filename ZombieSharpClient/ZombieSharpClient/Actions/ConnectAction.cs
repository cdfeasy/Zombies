using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using ZombieSharpClient.Entity.Game;
using ZombieSharpClient.Entity.Server;
using ZombieSharpClient.Entity.Support;
namespace ZombieSharpClient.Actions
{
	[DataContract]
	public class ConnectAction{
		[DataMember(Name = "pass")]
		public string Pass { get; set; }
	}
}