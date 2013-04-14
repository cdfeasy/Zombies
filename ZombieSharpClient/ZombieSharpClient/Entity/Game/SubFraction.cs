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
	public class SubFraction{
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
		[DataMember(Name = "level")]
		public int Level { get; set; }
		[DataMember(Name = "deck")]
		public List<Card> Deck { get; set; }
		[DataMember(Name = "fraction")]
		public Fraction Fraction { get; set; }
		[DataMember(Name = "abilities")]
		public List<Abilities> Abilities { get; set; }
		[DataMember(Name = "res1")]
		public int Res1 { get; set; }
		[DataMember(Name = "res2")]
		public int Res2 { get; set; }
		[DataMember(Name = "res3")]
		public int Res3 { get; set; }
	}
}