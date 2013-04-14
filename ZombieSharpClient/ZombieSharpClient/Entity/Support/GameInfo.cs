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
	public class GameInfo{
		[DataMember(Name = "enemyName")]
		public string EnemyName { get; set; }
		[DataMember(Name = "zombieKilled")]
		public int ZombieKilled { get; set; }
		[DataMember(Name = "survivalsKilled")]
		public int SurvivalsKilled { get; set; }
		[DataMember(Name = "xp")]
		public int Xp { get; set; }
		[DataMember(Name = "gold")]
		public int Gold { get; set; }
		[DataMember(Name = "win")]
		public bool Win { get; set; }
		[DataMember(Name = "gameType")]
		public byte GameType { get; set; }
		[DataMember(Name = "killed")]
		public Dictionary<long,int> Killed { get; set; }
		[DataMember(Name = "dead")]
		public Dictionary<long,int> Dead { get; set; }
		[DataMember(Name = "used")]
		public Dictionary<long,int> Used { get; set; }
	}
}