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
	public class TurnReply{
		[DataMember(Name = "action")]
		public int Action { get; set; }
		[DataMember(Name = "turnNumber")]
		public int TurnNumber { get; set; }
		[DataMember(Name = "nextTurnUser")]
		public int NextTurnUser { get; set; }
		[DataMember(Name = "cardId")]
		public long CardId { get; set; }
		[DataMember(Name = "position")]
		public int Position { get; set; }
		[DataMember(Name = "info")]
		public GameInfo Info { get; set; }
		[DataMember(Name = "actions")]
		public Dictionary<int,List<string>> Actions { get; set; }
		[DataMember(Name = "player1Card")]
		public Dictionary<int,List<CardWrapper>> Player1Card { get; set; }
		[DataMember(Name = "player2Card")]
		public Dictionary<int,List<CardWrapper>> Player2Card { get; set; }
		[DataMember(Name = "playerHand")]
		public List<long> PlayerHand { get; set; }
		[DataMember(Name = "res1")]
		public int Res1 { get; set; }
		[DataMember(Name = "res2")]
		public int Res2 { get; set; }
		[DataMember(Name = "res3")]
		public int Res3 { get; set; }
	}
}