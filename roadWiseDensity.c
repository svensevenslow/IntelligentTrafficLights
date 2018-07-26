#include <stdio.h>
int main()
{
    int density1 = 0, density2 = 0, density3 = 0, density4 = 0;
    //int lane;
    char lane[1];	//scanning from file in this format
    int realLane[1];	//converting it into this format
    int write[4];
    int i;    
    FILE *fp,*np;
    fp = fopen("myfile","r"); 
    
    //while(fscanf(fp,"%s",lane) != EOF){
    //printf("%c",lane[0]);
    
    fclose(fp);
    if (lane[0] == '1')
    {
        density1++;
        
    }
    if ( lane[0] == '2')
    {
        density2++;
        
    }
    if (lane[0] == '3')
    {
        density3++;
        
    }
    if (lane[0] == '4')
    {
        density4++;
        
    }
    
    write[0] = density1;
    write[1] = density2;
    write[2] = density3;   
    write[3] = density4;
    printf("%d %d %d %d ",density1,density2,density3,density4);
    np = fopen("abc.txt","w");
    //printf(np, "%d %d %d %d ", write[0], write[1], write[2], write[3]);
    fprintf(np, "%d %d %d %d %d %d %d %d", write[0], write[1], write[2], write[3],write[0], write[1], write[2], write[3]);
    fclose(np);
 
 		   
    
}
