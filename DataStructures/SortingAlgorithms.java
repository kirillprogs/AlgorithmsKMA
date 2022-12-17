/* Written by Kyrylo Pervushyn on 21 October 2022 */
import java.util.Arrays;
import java.util.Comparator;

public class SortingAlgorithms {

    /* Merge sort - time O(n*log(n)) and space O(n). */
    public static <T> void merge_sort(T[] array, Comparator<T> comparator) {
        range_merge_sort(array, comparator, 0, array.length - 1);
    }

    /* Quick sort - time O(n) - O(n^2) and space O(1). */
    public static <T> void quick_sort(T[] array, Comparator<T> comparator) {
        range_quick_sort(array, comparator, 0, array.length - 1);
    }

    /* Algorithm takes time O(n^2) in worst case and space O(1). */
    public static <T> void insertion_sort(T[] array, Comparator<T> comparator) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (comparator.compare(array[j], array[j - 1]) < 0)
                    swap(array, j, j - 1);
                else
                    break;
            }
        }
    }

    /* Algorithm takes time O(n^2) in average case and space O(1). */
    public static <T> void selection_sort(T[] array, Comparator<T> comparator) {
        for (int i = 1; i < array.length; ++i) {
            int max_id = 0;
            for (int j = 1; j <= array.length - i; ++j) {
                if (comparator.compare(array[j], array[max_id]) > 0)
                    max_id = j;
            }
            swap(array, max_id, array.length - i);
        }
    }

    /* Space O(1). */
    public static <T> void shell_sort(T[] array, Comparator<T> comparator) {
        int n = array.length;
        int interval = 1;
        while (interval < n / 3)
            interval = 3 * interval + 1; // 1, 4, 13, 40, 121, 364, ...
        while (interval >= 1) {
            for (int i = interval; i < n; i++) {
                for (int j = i; j >= interval; j -= interval) {
                    if (comparator.compare(array[j], array[j - interval]) < 0)
                        swap(array, j, j - interval);
                    else
                        break;
                }
            }
            interval = interval / 3;
        }
    }

    private static <T> void range_quick_sort(T[] array, Comparator<T> comparator,
                                             int lo, int hi) {
        if (lo >= hi) return;
        int idx = partition(array, comparator, lo, hi);
        range_quick_sort(array, comparator, lo, idx - 1);
        range_quick_sort(array, comparator, idx + 1, hi);
    }

    private static <T> int partition(T[] array, Comparator<T> comparator,
                                     int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (comparator.compare(array[++i], array[lo]) < 0)
                if (i == hi) break;
            while (comparator.compare(array[lo], array[--j]) < 0)
                if (j == lo) break;
            if (i >= j) break;
            swap(array, i, j);
        }
        swap(array, lo, j);
        return j;
    }

    private static <T> void range_merge_sort(T[] array,
                                             Comparator<T> comparator,
                                             int left, int right) {
        if (left >= right) return;
        int middle = (left + right) / 2;
        range_merge_sort(array, comparator, left, middle);
        range_merge_sort(array, comparator, middle + 1, right);
        merge(array, comparator, left, middle, right);
    }

    private static <T> void merge(T[] array, Comparator<T> comparator,
                                  int left, int middle, int right) {
        final int left_arr_length = middle - left + 1;
	    final int right_arr_length = right - middle;

        T[] left_array = Arrays.copyOfRange(array, left, middle + 1);
        T[] right_array = Arrays.copyOfRange(array, middle + 1, right + 1);

        int left_pos = 0, right_pos = 0, arr_pos = left;
        while (left_pos < left_arr_length && right_pos < right_arr_length) {
            if (comparator.compare(left_array[left_pos], right_array[right_pos]) <= 0)
                array[arr_pos++] = left_array[left_pos++];
            else
                array[arr_pos++] = right_array[right_pos++];
        }
        while (left_pos < left_arr_length)
            array[arr_pos++] = left_array[left_pos++];
        while (right_pos < right_arr_length)
            array[arr_pos++] = right_array[right_pos++];
    }

    private static void swap(Object[] array, int fst, int snd) {
        Object tmp = array[fst];
        array[fst] = array[snd];
        array[snd] = tmp;
    }
}