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
	public class UserReply{
        [DataMember(Name = "reply", EmitDefaultValue = false)]
		public int Reply { get; set; }
        [DataMember(Name = "connectionReply", EmitDefaultValue = false)]
		public ConnectionReply ConnectionReply { get; set; }
        [DataMember(Name = "turnReply", EmitDefaultValue = false)]
		public TurnReply TurnReply { get; set; }
        [DataMember(Name = "userInfoReply", EmitDefaultValue = false)]
		public UserInfoReply UserInfoReply { get; set; }
        [DataMember(Name = "errorReply", EmitDefaultValue = false)]
		public ErrorReply ErrorReply { get; set; }
        [DataMember(Name = "searchReply", EmitDefaultValue = false)]
		public SearchReply SearchReply { get; set; }
        [DataMember(Name = "stopSearchReply", EmitDefaultValue = false)]
		public StopSearchReply StopSearchReply { get; set; }
        [DataMember(Name = "cardInfoReply", EmitDefaultValue = false)]
		public CardInfoReply CardInfoReply { get; set; }
        [DataMember(Name = "successReply", EmitDefaultValue = false)]
		public SuccessReply SuccessReply { get; set; }
        [DataMember(Name = "saveDeckReply", EmitDefaultValue = false)]
		public SaveDeckReply SaveDeckReply { get; set; }
        [DataMember(Name = "gameStartedReply", EmitDefaultValue = false)]
		public GameStartedReply GameStartedReply { get; set; }
        [DataMember(Name = "achievementsReply", EmitDefaultValue = false)]
		public AchievementsReply AchievementsReply { get; set; }
	}
}