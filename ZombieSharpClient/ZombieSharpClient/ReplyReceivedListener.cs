using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ZombieSharpClient.UserReplys;

namespace ZombieSharpClient
{
    class UserActionArgs : EventArgs{
        public UserActionArgs(UserReply r)
        {
            reply = r;
        }
      public UserReply reply { get; set; }

}
}
