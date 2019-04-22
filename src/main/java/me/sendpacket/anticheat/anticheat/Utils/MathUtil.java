package me.sendpacket.anticheat.anticheat.Utils;

import java.math.BigDecimal;

public class MathUtil {

    public static double RoundNumber(double number, int round_decimals)
    {
        return BigDecimal.valueOf(number).setScale(round_decimals, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double GetDistanceXZ(double x, double z, double x2, double z2)
    {
        double distance_x_axis = GetDistanceBetween2Numbers(x, x2);
        double distance_z_axis = GetDistanceBetween2Numbers(z, z2);
        return (distance_x_axis + distance_z_axis);
    }

    public static float ClampYaw(float value) {
        value = value % 360.0F;

        if (value >= 180.0F) {
            value -= 360.0F;
        }

        if (value < -180.0F) {
            value += 360.0F;
        }

        return value;
    }

    public static double GetDistanceBetween2Numbers(double num1, double num2)
    {
        double total_distance = 0;

        if(num1 >= 0 && num2 >= 0)
        {
            if(num1 > num2)
            {
                total_distance = num1 - num2;
            }else{
                total_distance = num2 - num1;
            }
        }else{
            if(num1 < 0 && num2 >= 0)
            {
                total_distance = num2 - num1;
            }else{
                if(num2 < 0 && num1 >= 0)
                {
                    total_distance = num1 - num2;
                }else{
                    if(num1 < 0 && num2 < 0)
                    {
                        total_distance = Math.abs(num1 - (num2));
                    }
                }
            }
        }
        return total_distance;
    }

}
