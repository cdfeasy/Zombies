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
	public class GameStartedReply{
		[DataMember(Name = "enemy")]
		public User Enemy { get; set; }
		[DataMember(Name = "cards")]
		public List<long> Cards { get; set; }
		[DataMember(Name = "position")]
		public int Position { get; set; }
		[DataMember(Name = "res1")]
		public int Res1 { get; set; }
		[DataMember(Name = "res2")]
		public int Res2 { get; set; }
		[DataMember(Name = "res3")]
		public int Res3 { get; set; }
	}
}