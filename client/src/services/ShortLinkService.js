const API_URL = "http://localhost:8080/api"; 

export async function createShortLink(url) {
    try {
      const response = await fetch(`${API_URL}/shorten`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          url: url
        })
      });
  
      if (!response.ok) {
        throw new Error("Failed to create short link");
      }
  
      const shortLinkResult = await response.text();
      return shortLinkResult;
    } catch (error) {
      console.error("Error:", error);
      throw error;
    }
  }