using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using ZombieSharpClient.Entity.Game;
using ZombieSharpClient.Entity.Server;
namespace ZombieSharpClient.Entity.Support
{
	[DataContract]
	public class ImageResource{
		[DataMember(Name = "id")]
		public int Id { get; set; }
		[DataMember(Name = "description")]
		public string Description { get; set; }
		[DataMember(Name = "image")]
		public byte[] Image { get; set; }
	}
}