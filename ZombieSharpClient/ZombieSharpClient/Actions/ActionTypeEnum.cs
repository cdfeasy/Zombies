using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ZombieSharpClient.Actions
{
    enum ActionTypeEnum : int
    {
        CONNECT = 0,
        TURN = 10,
        SEARCH = 20,
        STOP_SEARCH = 30,
        GETUSERINFO = 40,
        SAVE_DECK = 50,
        SET_DECK_ACTIVE = 60,
        GET_CARD_INFO = 70,
        CREATE_USER = 80
    }
}
