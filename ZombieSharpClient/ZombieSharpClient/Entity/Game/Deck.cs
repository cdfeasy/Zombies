using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using ZombieSharpClient.Entity.Server;
using ZombieSharpClient.Entity.Support;
namespace ZombieSharpClient.Entity.Game
{
	[DataContract]
	public class Deck{
		[DataMember(Name = "id")]
		public long Id { get; set; }
		[DataMember(Name = "deckCards")]
		public List<Card> DeckCards { get; set; }
		[DataMember(Name = "name")]
		public string Name { get; set; }
		[DataMember(Name = "description")]
		public string Description { get; set; }
	}
}