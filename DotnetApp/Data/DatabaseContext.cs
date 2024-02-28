
namespace Data;

using Microsoft.EntityFrameworkCore;
using Models;


public class DatabaseContext : DbContext
{
    public DatabaseContext(DbContextOptions<DatabaseContext> options) : base(options)
    {
    }
    
    public DbSet<Fiche> Fiches { get; set; }
    public DbSet<Language> Languages { get; set; }
    public DbSet<Pays> Payss { get; set; }
    public DbSet<Tutorials> Tutorialss { get; set; }
    public DbSet<TypeLanguage> TypeLanguages { get; set; }
    
}
