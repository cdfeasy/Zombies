using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using ZombieSharpClient.Entity.Game;
using ZombieSharpClient.Entity.Support;
namespace ZombieSharpClient.Entity.Server
{
	[DataContract]
	public class History{
		[DataMember(Name = "id")]
		public long Id { get; set; }
		[DataMember(Name = "player1")]
		public User Player1 { get; set; }
		[DataMember(Name = "player2")]
		public User Player2 { get; set; }
		[DataMember(Name = "result")]
		public byte Result { get; set; }
		[DataMember(Name = "gameType")]
		public byte GameType { get; set; }
	}
}