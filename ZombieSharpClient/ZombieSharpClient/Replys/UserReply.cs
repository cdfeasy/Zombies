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
	public class UserReply{
		[DataMember(Name = "reply")]
		public int Reply { get; set; }
		[DataMember(Name = "connectionReply")]
		public ConnectionReply ConnectionReply { get; set; }
		[DataMember(Name = "turnReply")]
		public TurnReply TurnReply { get; set; }
		[DataMember(Name = "userInfoReply")]
		public UserInfoReply UserInfoReply { get; set; }
		[DataMember(Name = "errorReply")]
		public ErrorReply ErrorReply { get; set; }
		[DataMember(Name = "searchReply")]
		public SearchReply SearchReply { get; set; }
		[DataMember(Name = "stopSearchReply")]
		public StopSearchReply StopSearchReply { get; set; }
		[DataMember(Name = "cardInfoReply")]
		public CardInfoReply CardInfoReply { get; set; }
		[DataMember(Name = "successReply")]
		public SuccessReply SuccessReply { get; set; }
		[DataMember(Name = "saveDeckReply")]
		public SaveDeckReply SaveDeckReply { get; set; }
		[DataMember(Name = "gameStartedReply")]
		public GameStartedReply GameStartedReply { get; set; }
		[DataMember(Name = "achievementsReply")]
		public AchievementsReply AchievementsReply { get; set; }
	}
}