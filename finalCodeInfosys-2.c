#include<stdio.h>
#include<math.h>
float cycletime = 120;      //120s is the entire cycle
int counter = 2;
void greentime( float* num_cars, float totalv, float EffGree, float* yellowLight)    //this function prints the green time of respective roads 1, 2, 3, 4
{
    
    printf("Cycle %d\n", counter++);
    float greenLight[4];        //greenLight stores green light times for each direction
    int i = 0;
    printf("Green light: ");
    for ( i = 0 ; i < 4 ; i++ )
    {
        greenLight[i]=((num_cars[i]/totalv)*EffGree);       //num_cars stores number of cars in each direction
        printf("%f  " ,round(greenLight[i]));               //prints the green light times
        
    }
    printf("\n");
    float redLight[4];          //redLight stores the red light times for each direction
    
    redLight[0] = greenLight[0] + greenLight[1] + greenLight[2] + greenLight[3] + yellowLight[0] + yellowLight[1] + yellowLight[2] + yellowLight[3];
    redLight[1] = greenLight[0] + yellowLight[0];
    redLight[2] = redLight[1] + greenLight[1] + yellowLight[1];
    redLight[3] = redLight[2] + greenLight[2] + yellowLight[2];
    
    printf("Red light: ");
    for ( i = 0 ; i < 4 ; i++ )
    {
        printf("%f  " ,round(redLight[i]));               //prints the red light times
        
    }
    printf("\nYellow light: ");
    for(i=0;i<4;i++)
    {
        printf("%f ", yellowLight[i]);
    }
    printf("\n");
}

int main()

{   int i;
    int testCases = 1000;
    int flag = 0;
    float reaction1 = 3.5;      //reaction times for corresponding roads
    float reaction2 = 2.5;      //this is also the yellow light time for each road
    float reaction3 = 2.0;
    float reaction4 = 2.7;
    float yellowTime[4] = {reaction1, reaction2, reaction3, reaction4};
    float EffGree ;
    FILE *fp;
    fp = fopen("abc.txt", "r");
    
    
    //float Vc1,Vc2,Vc3,Vc4;
    float totalv=0, round_one=0;
    float reaction = reaction1 + reaction2 + reaction3 + reaction4; //total reaction time
    float alphaValue = 0.6;     //60% historical data, 40% real time data is being taken
    /*float g1,g2,g3,g4;*/
    
    EffGree = cycletime - reaction;     //effective green time
    float realDensity[4];               //real time data of no. of cars
    float historicalDensity[4];     //historical data of no. of cars
    float combinedDensity[4];       //after combining historical and real time data of no. of cars
    char allDensities[20];
    int x = 1;
    while(x--){
        totalv=0;
        printf("\n");
        fgets(allDensities, 20, fp);
        sscanf(allDensities, "%f %f %f %f %f %f %f %f", &realDensity[0], &realDensity[1], &realDensity[2], &realDensity[3], &historicalDensity[0], &historicalDensity[1], &historicalDensity[2], &historicalDensity[3]);
        
        /*for(i=0;i<4;i++)
            scanf("%f",&realDensity[i]);
        
        for(i=0;i<4;i++)
            scanf("%f",&historicalDensity[i]);*/
        
        for (i = 0 ; i < 4 ; i++ )
        {
            combinedDensity[i] = alphaValue*historicalDensity[i] + (1-alphaValue)*(realDensity[i]);
        }
        if (combinedDensity[0] < 4 && combinedDensity[1] < 4 && combinedDensity[2] < 4 && combinedDensity[3] < 4)        //first time when all densities are less than 4, we set flag = 1
        {
            flag++;
        }
        /*else if (combinedDensity[0] < 4 && combinedDensity[1] < 4 &&combinedDensity[2] < 4 &&combinedDensity[3] < 4 && flag >= 1)       //subsequent and continuous times when all densities are less, we increment flag
         {
         flag++;
         }*/
        else
        {
            flag = 0;       //if there is a break in condition of less cars, we reset the conditions
        }
        
        for(i=0;i<4;i++)
        {
            totalv=totalv+combinedDensity[i];       //total no. of cars is calculated
        }
        
        if(round_one==0)      //on the first round
        {
            
            printf("Cycle 1\nGreen light: ");
            for(i=0;i<4;i++)
            {
                printf("%f ", cycletime/4);
            }
            printf("\nRed light: ");
            for ( i = 4 ; i >= 1 ; i-- )
            {
                printf("%f ", (cycletime/4)*i);
            }
            printf("\nYellow light: ");
            for(i=0;i<4;i++)
            {
                printf("%f ", yellowTime[i]);
            }
            round_one++;
            
        }
        else if (flag >= 10)     //if less than 4 cars are there on each road for last 10 cycles
        {
            printf("Cycle %d\n", counter++);
            float zero = 0;
            printf("\nGreen light: %f %f %f %f", zero, zero, zero, zero);
            printf("\nRed light: %f %f %f %f", zero, zero, zero, zero);
            printf("\nYellow light: ");
            printf("%f %f %f %f", cycletime, cycletime, cycletime, cycletime);
            
        }
        else greentime(combinedDensity,totalv, EffGree, yellowTime);        //i+1 th road
        
        
        
        
        
        
    } //while loop ends
    
    
    
    
    
}
