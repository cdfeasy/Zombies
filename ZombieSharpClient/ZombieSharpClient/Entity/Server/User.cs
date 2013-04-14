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
	public class User{
		[DataMember(Name = "id")]
		public long Id { get; set; }
		[DataMember(Name = "name")]
		public string Name { get; set; }
		[DataMember(Name = "pass")]
		public string Pass { get; set; }
		[DataMember(Name = "side")]
		public long Side { get; set; }
		[DataMember(Name = "decks")]
		public List<Deck> Decks { get; set; }
		[DataMember(Name = "decksIds")]
		public List<DeckInfo> DecksIds { get; set; }
		[DataMember(Name = "availableCards")]
		public List<Card> AvailableCards { get; set; }
		[DataMember(Name = "availableCardsIds")]
		public List<long> AvailableCardsIds { get; set; }
		[DataMember(Name = "activeDeck")]
		public Deck ActiveDeck { get; set; }
		[DataMember(Name = "friendList")]
		public FriendList FriendList { get; set; }
		[DataMember(Name = "activeDeckIds")]
		public DeckInfo ActiveDeckIds { get; set; }
		[DataMember(Name = "level")]
		public int Level { get; set; }
		[DataMember(Name = "xp")]
		public int Xp { get; set; }
		[DataMember(Name = "gold")]
		public int Gold { get; set; }
		[DataMember(Name = "payed")]
		public int Payed { get; set; }
		[DataMember(Name = "zombieKilled")]
		public int ZombieKilled { get; set; }
		[DataMember(Name = "survivalsKilled")]
		public int SurvivalsKilled { get; set; }
		[DataMember(Name = "avatar")]
		public byte[] Avatar { get; set; }
	}
}