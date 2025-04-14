package com.javaweb.service;

import com.javaweb.model.gennerate.BuildingGennerate;

public interface GeminiAIService {
    String generateDescription(BuildingGennerate building);
}
