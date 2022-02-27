package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.SizeEntity;
import com.example.pizzaservice.model.Size;

import java.util.Optional;

public class SizeMapper {
    public Optional<Size> mapEntityToSize(SizeEntity entity) {

        if(entity == null){
            return Optional.empty();
        }

        Size size = new Size(entity.getId(), entity.getDiameter());

        return Optional.of(size);
    }

    public SizeEntity mapSizeToEntity(Size size) {

        return new SizeEntity(size.getId(), size.getDiameter());
    }
}
