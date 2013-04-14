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
	public class UserInfoReply{
		[DataMember(Name = "user")]
		public User User { get; set; }
	}
}