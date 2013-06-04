using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using ZombieSharpClient.Entity.Game;
using ZombieSharpClient.Entity.Server;
using ZombieSharpClient.Entity.Support;
namespace ZombieSharpClient.UserActions
{
	[DataContract]
	public class CreateUserAction{
		[DataMember(Name = "name")]
		public string Name { get; set; }
		[DataMember(Name = "pass")]
		public string Pass { get; set; }
		[DataMember(Name = "side")]
		public long Side { get; set; }
	}
}