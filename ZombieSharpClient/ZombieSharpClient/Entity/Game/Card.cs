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
	public class Card{
		[DataMember(Name = "id")]
		public long Id { get; set; }
		[DataMember(Name = "name")]
		public string Name { get; set; }
		[DataMember(Name = "nameEng")]
		public string NameEng { get; set; }
		[DataMember(Name = "description")]
		public string Description { get; set; }
		[DataMember(Name = "descriptionEng")]
		public string DescriptionEng { get; set; }
		[DataMember(Name = "strength")]
		public int Strength { get; set; }
		[DataMember(Name = "hp")]
		public int Hp { get; set; }
		[DataMember(Name = "armour")]
		public int Armour { get; set; }
		[DataMember(Name = "img")]
		public byte[] Img { get; set; }
		[DataMember(Name = "shortImg")]
		public byte[] ShortImg { get; set; }
		[DataMember(Name = "threadLevel")]
		public int ThreadLevel { get; set; }
		[DataMember(Name = "cardType")]
		public int CardType { get; set; }
		[DataMember(Name = "cardLevel")]
		public int CardLevel { get; set; }
		[DataMember(Name = "cardGoldCost")]
		public int CardGoldCost { get; set; }
		[DataMember(Name = "uniqueCard")]
		public bool UniqueCard { get; set; }
		[DataMember(Name = "abilities")]
		public List<Abilities> Abilities { get; set; }
		[DataMember(Name = "subFraction")]
		public SubFraction SubFraction { get; set; }
		[DataMember(Name = "uniqueAbility")]
		public Abilities UniqueAbility { get; set; }
		[DataMember(Name = "resourceCost1")]
		public int ResourceCost1 { get; set; }
		[DataMember(Name = "resourceCost2")]
		public int ResourceCost2 { get; set; }
		[DataMember(Name = "resourceCost3")]
		public int ResourceCost3 { get; set; }
	}
}