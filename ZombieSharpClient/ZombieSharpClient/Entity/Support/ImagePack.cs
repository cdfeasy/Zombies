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
	public class ImagePack{
		[DataMember(Name = "images")]
		public List<ImageResource> Images { get; set; }
	}
}