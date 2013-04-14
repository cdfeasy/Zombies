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
	public class GameVersion{
		[DataMember(Name = "id")]
		public int Id { get; set; }
		[DataMember(Name = "version")]
		public int Version { get; set; }
	}
}