using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using ZombieSharpClient.Entity.Game;
using ZombieSharpClient.Entity.Server;
using ZombieSharpClient.Entity.Support;
namespace ZombieSharpClient.UserReply
{
	[DataContract]
	public class SaveDeckReply{
		[DataMember(Name = "deckId")]
		public int DeckId { get; set; }
	}
}