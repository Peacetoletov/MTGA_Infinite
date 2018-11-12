import java.util.Random;

/**
 * Created by lukas on 12.11.2018.
 */

public class Simulator {
    private final int seriesPlayed = 1000000;     //How many times the event was entered
    private final float baseWinRate = 50.0f;
    private final float winRateIncrement = 0.1f;
    private Random r = new Random();
    private enum EventType {
        CONSTRUCTED_EVENT, COMP_CONSTRUCTED, DRAFT, COMP_DRAFT
    }

    public Simulator() {
        simulate(EventType.CONSTRUCTED_EVENT, 500, false, 7, 3, baseWinRate);
        simulate(EventType.COMP_CONSTRUCTED, 1000, true, 5, 2, baseWinRate);
        simulate(EventType.DRAFT, 750, false, 7, 3, baseWinRate);
        simulate(EventType.COMP_DRAFT, 1500, true, 5, 2, baseWinRate);
    }

    private void simulate(EventType type, int entryCost, boolean bo3, int maxWins, int maxLosses, float winRate) {
        int currency = 0;
        for (int i = 0; i < seriesPlayed; i++) {
            //Enter
            currency -= entryCost;

            //Play
            int wins = play(bo3, maxWins, maxLosses, winRate);

            //Get rewards
            currency += getRewards(type, wins);
        }

        if (currency < 0) {
            simulate(type, entryCost, bo3, maxWins, maxLosses, winRate + winRateIncrement);
        } else {
            System.out.println("Win rate of " + winRate + "% was enough to go infinite.");
        }
    }

    private int play(boolean bo3, int maxWins, int maxLosses, float winRate) {
        int wins = 0;
        int losses = 0;
        float winRateDecimal = winRate / 100;
        while((wins < maxWins) && losses < maxLosses) {
            //Best of 1
            if (!bo3) {
                if (r.nextFloat() < winRateDecimal) {
                    wins++;
                } else {
                    losses++;
                }
            }
            //Best of 3
            else {
                int smallWins = 0;
                int smallLosses = 0;
                while ((smallWins < 2) && (smallLosses < 2)) {
                    if (r.nextFloat() < winRateDecimal) {
                        smallWins++;
                    } else {
                        smallLosses++;
                    }
                }
                if (smallWins == 2) {
                    wins++;
                } else {
                    losses++;
                }
            }
        }
        return wins;
    }

    private int getRewards(EventType type, int wins) {
        int reward = -1;
        switch (type) {
            case CONSTRUCTED_EVENT:
                switch (wins) {
                    case 0:
                        reward = 100;
                        break;
                    case 1:
                        reward = 200;
                        break;
                    case 2:
                        reward = 300;
                        break;
                    case 3:
                        reward = 400;
                        break;
                    case 4:
                        reward = 500;
                        break;
                    case 5:
                        reward = 600;
                        break;
                    case 6:
                        reward = 800;
                        break;
                    case 7:
                        reward = 1000;
                        break;
                    default:
                        System.exit(1);
                }
                break;

            case COMP_CONSTRUCTED:
                switch (wins) {
                    case 0:
                        reward = 0;
                        break;
                    case 1:
                        reward = 500;
                        break;
                    case 2:
                        reward = 1000;
                        break;
                    case 3:
                        reward = 1500;
                        break;
                    case 4:
                        reward = 1700;
                        break;
                    case 5:
                        reward = 2100;
                        break;
                    default:
                        System.exit(1);
                }
                break;

            case DRAFT:
                switch (wins) {
                    case 0:
                        reward = 50;
                        break;
                    case 1:
                        reward = 100;
                        break;
                    case 2:
                        reward = 200;
                        break;
                    case 3:
                        reward = 300;
                        break;
                    case 4:
                        reward = 450;
                        break;
                    case 5:
                        reward = 650;
                        break;
                    case 6:
                        reward = 850;
                        break;
                    case 7:
                        reward = 950;
                        break;
                    default:
                        System.exit(1);
                }
                break;

            case COMP_DRAFT:
                switch (wins) {
                    case 0:
                        reward = 0;
                        break;
                    case 1:
                        reward = 0;
                        break;
                    case 2:
                        reward = 800;
                        break;
                    case 3:
                        reward = 1500;
                        break;
                    case 4:
                        reward = 1800;
                        break;
                    case 5:
                        reward = 2100;
                        break;
                    default:
                        System.exit(1);
                }
                break;
        }

        if (reward == -1) {
            System.exit(1);
        }
        return reward;
    }

}
