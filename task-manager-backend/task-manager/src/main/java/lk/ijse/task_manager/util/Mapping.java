package lk.ijse.task_manager.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapping {
    private final ModelMapper modelMapper;

    public <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public <D, T> D map(final T entity, D out) {
        modelMapper.map(entity, out);
        return out;
    }
}
