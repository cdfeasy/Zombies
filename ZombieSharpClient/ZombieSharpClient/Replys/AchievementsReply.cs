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
	public class AchievementsReply{
		[DataMember(Name = "type")]
		public byte Type { get; set; }
		[DataMember(Name = "value")]
		public int Value { get; set; }
		[DataMember(Name = "descriptionEng")]
		public string DescriptionEng { get; set; }
		[DataMember(Name = "description")]
		public string Description { get; set; }
	}
}