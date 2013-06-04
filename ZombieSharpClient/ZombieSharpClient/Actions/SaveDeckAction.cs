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
	public class SaveDeckAction{
		[DataMember(Name = "deckId")]
		public int DeckId { get; set; }
		[DataMember(Name = "cardsIds")]
		public List<int> CardsIds { get; set; }
	}
}