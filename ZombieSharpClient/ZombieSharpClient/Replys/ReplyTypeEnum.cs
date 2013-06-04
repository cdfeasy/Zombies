using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ZombieSharpClient.Replys
{

    internal enum ReplyTypeEnum : int
    {
           ERROR=0,
    TURN=10,
    CONNECTION=20,
    USERINFO=30,
    SEARCH=40,
    STOP_SEARCH=50,
    SAVE_DECK=60,
    SET_DECK_ACTIVE=70,
    GET_CARD_INFO=80,
    GAME_STARTED=90,
    ACHIEVEMENT=100
    }
}
