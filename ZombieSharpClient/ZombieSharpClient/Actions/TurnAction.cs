using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using ZombieSharpClient.Entity.Game;
using ZombieSharpClient.Entity.Server;
using ZombieSharpClient.Entity.Support;
namespace ZombieSharpClient.UserActions
{
	[DataContract]
	public class TurnAction{
		[DataMember(Name = "action")]
		public int Action { get; set; }
		[DataMember(Name = "turnNumber")]
		public int TurnNumber { get; set; }
		[DataMember(Name = "cardId")]
		public long CardId { get; set; }
		[DataMember(Name = "position")]
		public int Position { get; set; }
	}
}