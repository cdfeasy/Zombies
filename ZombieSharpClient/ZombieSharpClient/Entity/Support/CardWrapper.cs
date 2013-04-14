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
	public class CardWrapper{
		[DataMember(Name = "card")]
		public Card Card { get; set; }
		[DataMember(Name = "cardId")]
		public long CardId { get; set; }
		[DataMember(Name = "wrapperId")]
		public int WrapperId { get; set; }
		[DataMember(Name = "strengthBuff")]
		public byte StrengthBuff { get; set; }
		[DataMember(Name = "hp")]
		public byte Hp { get; set; }
		[DataMember(Name = "armourBuff")]
		public byte ArmourBuff { get; set; }
		[DataMember(Name = "virus")]
		public byte Virus { get; set; }
		[DataMember(Name = "active")]
		public bool Active { get; set; }
	}
}