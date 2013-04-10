using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ZombieSharpClient.Actions
{
    [DataContract]
    public class ConnectAction
    {
        [DataMember(Name = "pass")]
        public string Pass  { get; set; }
    }
}
