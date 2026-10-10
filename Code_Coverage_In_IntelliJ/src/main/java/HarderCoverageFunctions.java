public class HarderCoverageFunctions {


    String castSpells(int monsterHealth, int manaLevel, int wandDurability, int queuedSpells)
    {
        for (int i = 0; i < queuedSpells; i++)
        {
            if(manaLevel <= 0 || wandDurability <= 0)
            {
                return "The spell fizzles in front of you.";
            }
            else
            {
                monsterHealth -= 3;
                manaLevel -= 10;
                wandDurability--;
                if(monsterHealth <= 0)
                {
                    return "The spell destroys the monster!";
                }
                else
                {
                    return "The spell zaps the monster!";
                }
            }

        }

        if(monsterHealth <= 0)
        {
            return "You win!";
        }
        else {
            return "Monster is still alive";
        }
    }

    String truthFinder(boolean a, boolean b, boolean c)
    {
        if (a || b)
        {
            if (b && c)
            {
                return "YOU FOUND THE TRUTH!";
            } else if (a && c) {
                return "Well I guess that is true too";
            }
            else {
                return "Well that might be true";
            }
        } else {
            return "Are you even trying to find the truth?";
        }
    }

}