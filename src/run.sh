sizes=(1000 3000 5000 7000 9000 11000 13000 15000)

for size in "${sizes[@]}"
do
    echo "=== Running test with input size: $size ==="
    java proj3.java _EXPANDED_AllTimePremierLeaguePlayerStatistics.csv bubblesort $size
    echo
done

for size in "${sizes[@]}"
do
    echo "=== Running test with input size: $size ==="
    java proj3.java _EXPANDED_AllTimePremierLeaguePlayerStatistics.csv transpositionsort $size
    echo
done


for size in "${sizes[@]}"
do
    echo "=== Running test with input size: $size ==="
    java proj3.java _EXPANDED_AllTimePremierLeaguePlayerStatistics.csv mergesort $size
    echo
done

for size in "${sizes[@]}"
do
    echo "=== Running test with input size: $size ==="
    java proj3.java _EXPANDED_AllTimePremierLeaguePlayerStatistics.csv heapsort $size
    echo
done

for size in "${sizes[@]}"
do  
    echo "=== Running test with input size: $size ==="
    java proj3.java _EXPANDED_AllTimePremierLeaguePlayerStatistics.csv quicksort $size
    echo
done


echo "All runs completed."
