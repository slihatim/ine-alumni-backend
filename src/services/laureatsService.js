import api from "./api";

export const laureatsService = {
  // Get all laureates with pagination and sorting
  getAllLaureates: async (params = {}) => {
    const {
      page = 0,
      size = 12,
      sortBy = "fullName",
      sortDir = "asc",
    } = params;

    const response = await api.get("/laureats", {
      params: { page, size, sortBy, sortDir },
    });
    return response.data;
  },

  // Search laureates
  searchLaureates: async (searchTerm, params = {}) => {
    const { page = 0, size = 12 } = params;
    const response = await api.get("/laureats/search", {
      params: { q: searchTerm, page, size },
    });
    return response.data;
  },

  // Filter laureates with advanced criteria
  filterLaureates: async (filterData, params = {}) => {
    const { page = 0, size = 12 } = params;
    const response = await api.post("/laureats/filter", filterData, {
      params: { page, size },
    });
    return response.data;
  },

  // Get laureate details by ID
  getLaureateById: async (id) => {
    const response = await api.get(`/laureats/${id}`);
    return response.data;
  },
};
