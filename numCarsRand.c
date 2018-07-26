#include<stdio.h>
#include<stdlib.h>
#include<time.h>

int main()
{	int n,i;
	n = 1000;
	srand(time(NULL));
	
	while(n--){
		for(i = 0 ; i < 8;i++){
			printf("%d %d %d %d %d %d %d %d\n",rand() % 15, rand() % 15 ,rand() % 15, rand() % 15,rand() % 15, rand() % 15 ,rand() % 15, rand() % 15);
		}
	}

return 0;
}
