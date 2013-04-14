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
	public class DetailStatistic{
		[DataMember(Name = "id")]
		public long Id { get; set; }
		[DataMember(Name = "user")]
		public User User { get; set; }
		[DataMember(Name = "cardId")]
		public long CardId { get; set; }
		[DataMember(Name = "killing")]
		public int Killing { get; set; }
		[DataMember(Name = "dead")]
		public int Dead { get; set; }
		[DataMember(Name = "used")]
		public int Used { get; set; }
	}
}