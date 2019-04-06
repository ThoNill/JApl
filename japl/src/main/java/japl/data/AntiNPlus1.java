package japl.data;

import java.util.Collection;
import java.util.Map;

import japl.basis.AplRuntimeException;
import japl.basis.UnaryFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

public class AntiNPlus1 implements UnaryFunction {

    public Flux<Map<String, Collection<AntiNplus1Data>>> combine(
            Flux<AntiNplus1Data>[] fluxes) {
        Flux<AntiNplus1Data> ordered = Flux.mergeOrdered(new AntiNplus1DataComparator(),fluxes);
        Flux<GroupedFlux<String, AntiNplus1Data>> groupedFluxFlux = ordered
                .groupBy(data -> data.getId());
        return groupedFluxFlux
                .map(groupedFlux -> groupedFlux.collectMultimap(
                        data -> data.getName()).block());
    }

    @Override
    public Object apply(Object omega) {
        if (omega instanceof Object[]) {
            Flux<?>[] fluxes = convertToFluxArray((Object[]) omega);
            return combine((Flux<AntiNplus1Data>[]) fluxes);
        }
        throw new AplRuntimeException(
                "Argument must be a Flux<AntiNplus1Data>[]");
    }

    private Flux<?>[] convertToFluxArray(Object[] array) {
        Flux<?>[] fluxes = new Flux<?>[array.length];
        try {
            for (int i = 0; i < array.length; i++) {
                fluxes[i] = (Flux<?>) array[i];
            }                
        } catch (Exception ex) {
            throw new AplRuntimeException(
                    "Argument must be a Flux<AntiNplus1Data>[]", ex);
        }
        return fluxes;
    }

}
