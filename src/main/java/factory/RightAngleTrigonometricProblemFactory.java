package factory;

import model.RightAngleTrigonometric;
import util.Utility;

public class RightAngleTrigonometricProblemFactory {
    private static final int TRIG_FIND_ANGLE_GIVEN_2_SIDES = 0;
    private static final int TRIG_FIND_SIDE_GIVEN_2_SIDES = 1;
    private static final int TRIG_FIND_ANGLE_GIVEN_2_ANGLES = 2;
    private static final int TRIG_FIND_SIDE_GIVEN_SIDE_AND_ANGLE = 3;
    private static final char[] possibleTargetSides = {'x', 'y', 'x'};
    private static final char[] possibleTargetAngles = {'α', 'β', 'θ'};

    public static RightAngleTrigonometric generateRightAngleTrigonometricProblem(int given) {
       return generateProblem(given);
    }

    public static RightAngleTrigonometric generateRightAngleTrigonometricProblem(int given, String question) {
        RightAngleTrigonometric problem = generateProblem(given);
        if (problem != null) {
            problem.setQuestion(question);
        }
        return problem;
    }
    private static RightAngleTrigonometric generateProblem(int given){
        double[] sides = new double[3];
        double[] angles = new double[2];
        boolean[] isGivenSide = new boolean[3];
        boolean[] isGivenAngle = new boolean[2];
        //3 sides, then 2 angles
        boolean[] isTarget = new boolean[5];
        int unit = (int) Utility.getRandomNumber(0, 3);
        sides[0] = Utility.getRandomNumber(10, 40);
        sides[1] = Utility.getRandomNumber(10, 40);
        sides[2] = Math.sqrt((sides[0] * sides[0]) + (sides[1] * sides[1]));
        angles[0] = Math.atan(sides[1] / sides[0]);
        angles[1] = Math.atan(sides[0] / sides[1]);
        int targetIndex = (int) Utility.getRandomNumber(0, 2);
        angles = Utility.toDegrees(angles);
        switch (given) {
            case TRIG_FIND_ANGLE_GIVEN_2_ANGLES: {
                isGivenAngle[0] = true;
                isTarget[4] = true;
                return new RightAngleTrigonometric(possibleTargetAngles[targetIndex], angles[1], unit, sides, angles, isGivenSide, isGivenAngle, given, isTarget);
            }
            case TRIG_FIND_ANGLE_GIVEN_2_SIDES: {
                int first = (int) Utility.getRandomNumber(0, 2);
                int second = (int) Utility.getRandomNumber(0, 2);
                while (second == first) {
                    second = (int) Utility.getRandomNumber(0, 2);
                }
                isGivenSide[first] = true;
                isGivenSide[second] = true;
                int target = (int) Utility.getRandomNumber(3, 4);
                isTarget[target] = true;

                return new RightAngleTrigonometric(possibleTargetAngles[targetIndex], angles[target - 3], unit, sides, angles, isGivenSide, isGivenAngle, given, isTarget);
            }
            case TRIG_FIND_SIDE_GIVEN_2_SIDES: {
                int first = (int) Utility.getRandomNumber(0, 2);
                int second = (int) Utility.getRandomNumber(0, 2);
                while (second == first) {
                    second = (int) Utility.getRandomNumber(0, 2);
                }
                int third = 3 - first - second;
                isGivenSide[first] = true;
                isGivenSide[second] = true;
                isTarget[third] = true;
                return new RightAngleTrigonometric(possibleTargetSides[targetIndex], sides[third], unit, sides, angles, isGivenSide, isGivenAngle, given, isTarget);
            }
            case TRIG_FIND_SIDE_GIVEN_SIDE_AND_ANGLE: {
                int givenside = (int) Utility.getRandomNumber(0, 2);
                isGivenSide[givenside] = true;
                isGivenAngle[(int) Utility.getRandomNumber(0, 1)] = true;

                int target = (int) Utility.getRandomNumber(0, 2);
                while (target == givenside) {
                    target = (int) Utility.getRandomNumber(0, 2);
                }
                isTarget[target] = true;

                return new RightAngleTrigonometric(possibleTargetSides[targetIndex], sides[target], unit, sides, angles, isGivenSide, isGivenAngle, given, isTarget);
            }
        }
        return null;
    }

}
