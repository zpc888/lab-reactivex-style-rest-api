package prot.lab.reactivex;

import reactor.core.publisher.Flux;

public class FluxCreatorTest {
    public static void main(String[] args) {
        Flux<Integer> odd = Flux.fromArray(new Integer[]{1, 3, 5, 7, 9});
        Flux<Integer> even = Flux.fromArray(new Integer[]{2, 4, 6, 8, 10, 12});
        odd.zipWith(even).subscribe( xy -> System.out.printf("odd = %s; even = %s%n", xy.getT1(), xy.getT2()));

    }
}
