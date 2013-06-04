using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ZombieSharpClient.Entity.Game
{
    internal enum CardTypeEnum : int
    {
         creature=0,
    bigCreature=1,
    vehicle=2,
    transport=3,
    structure=4,
    damageSpell=5,
    buffSpell=6,
    globalSpell=7
    }
}
