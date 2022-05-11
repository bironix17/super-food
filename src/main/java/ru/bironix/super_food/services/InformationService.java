package ru.bironix.super_food.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bironix.super_food.store.fileStore.models.DeliveryInformation;
import ru.bironix.super_food.store.UpdateMapper;
import ru.bironix.super_food.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class InformationService {

    private final UpdateMapper updateMapper;
    final private Path deliveryFilePath =
            Paths.get("src/main/java/ru/bironix/super_food/store/fileStore/saveFiles/delivery_information.json");
    private DeliveryInformation deliveryInformation;

    @Autowired
    public InformationService(UpdateMapper updateMapper) {
        this.updateMapper = updateMapper;
        if (Files.notExists(deliveryFilePath)) {
            try {
                Files.createFile(deliveryFilePath);
            } catch (IOException e) {
            }
        }
        try {
            deliveryInformation = Utils.customMapper
                    .readValue(new File(String.valueOf(deliveryFilePath)), DeliveryInformation.class);
        } catch (IOException e) {
            deliveryInformation = new DeliveryInformation(200);
        }
    }

    public DeliveryInformation getDeliveryInformation() {
        return deliveryInformation;
    }

    public DeliveryInformation updateDeliveryInformation(DeliveryInformation delInfo) {
         updateMapper.map(delInfo, deliveryInformation);
        try {
            Utils.customMapper.writeValue(new File(String.valueOf(deliveryFilePath)), deliveryInformation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return deliveryInformation;
    }
}