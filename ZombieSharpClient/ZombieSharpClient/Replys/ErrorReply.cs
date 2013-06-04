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
	public class ErrorReply{
		[DataMember(Name = "errorText")]
		public string ErrorText { get; set; }
		[DataMember(Name = "errorCode")]
		public int ErrorCode { get; set; }
	}
}