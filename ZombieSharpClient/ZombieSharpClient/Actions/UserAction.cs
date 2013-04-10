using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ZombieSharpClient.Actions
{
    [DataContract]
    public class UserAction
    {
        [DataMember(Name = "action")]
        public int Action { get; set; }

        [DataMember(Name = "token")]
        public string Token { get; set; }

        [DataMember(Name = "name")]
        public string Name { get; set; }

        [DataMember(Name = "connectAction")]
        public ConnectAction ConnectAction { get; set; }
    }
}
