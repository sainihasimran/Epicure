//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace EpicureService
{
    using Newtonsoft.Json;
    using System;
    using System.Collections.Generic;
    
    public partial class Recipe
    {
        public Recipe()
        {
            this.Ingredients = new HashSet<Ingredient>();
            this.Steps = new HashSet<Step>();
        }
    
        public string Name { get; set; }
        public string Image { get; set; }
        public int ServingSize { get; set; }
        public string Category { get; set; }
        public int Duration { get; set; }
        public int Calories { get; set; }
        public int RecipeId { get; set; }

        [JsonProperty(NullValueHandling = NullValueHandling.Ignore)]
        public virtual ICollection<Ingredient> Ingredients { get; set; }
        [JsonProperty(NullValueHandling = NullValueHandling.Ignore)]
        public virtual ICollection<Step> Steps { get; set; }
    }
}
