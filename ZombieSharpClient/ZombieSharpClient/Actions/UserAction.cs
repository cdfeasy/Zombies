using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using ZombieSharpClient.Entity.Game;
using ZombieSharpClient.Entity.Server;
using ZombieSharpClient.Entity.Support;
namespace ZombieSharpClient.Actions
{
	[DataContract]
	public class UserAction{
		[DataMember(Name = "action")]
		public int Action { get; set; }
		[DataMember(Name = "token")]
		public string Token { get; set; }
		[DataMember(Name = "name")]
		public string Name { get; set; }
		[DataMember(Name = "connectAction")]
		public ConnectAction ConnectAction { get; set; }
		[DataMember(Name = "userInfo")]
		public UserInfoAction UserInfo { get; set; }
		[DataMember(Name = "searchAction")]
		public SearchAction SearchAction { get; set; }
		[DataMember(Name = "turnAction")]
		public TurnAction TurnAction { get; set; }
		[DataMember(Name = "cardInfoAction")]
		public CardInfoAction CardInfoAction { get; set; }
		[DataMember(Name = "setDeckActiveAction")]
		public SetDeckActiveAction SetDeckActiveAction { get; set; }
		[DataMember(Name = "saveDeckAction")]
		public SaveDeckAction SaveDeckAction { get; set; }
		[DataMember(Name = "createUserAction")]
		public CreateUserAction CreateUserAction { get; set; }
	}
}