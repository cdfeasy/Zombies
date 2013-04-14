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
	public class Fraction{
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
		[DataMember(Name = "subFractions")]
		public List<SubFraction> SubFractions { get; set; }
	}
}