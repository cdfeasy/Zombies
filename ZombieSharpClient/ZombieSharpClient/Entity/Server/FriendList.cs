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
	public class FriendList{
		[DataMember(Name = "id")]
		public long Id { get; set; }
		[DataMember(Name = "friends")]
		public List<long> Friends { get; set; }
		[DataMember(Name = "sendedRequest")]
		public List<long> SendedRequest { get; set; }
		[DataMember(Name = "receivedRequest")]
		public List<long> ReceivedRequest { get; set; }
	}
}