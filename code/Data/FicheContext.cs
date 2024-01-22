namespace Data;

using Microsoft.EntityFrameworkCore;
using Models;


public class FicheContext : DbContext
{
    public FicheContext(DbContextOptions<FicheContext> options) : base(options)
    {
    }
    
    public DbSet<Fiche> Fiches { get; set; }
}
